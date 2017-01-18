//存放主要交互逻辑js代码
//js模块化
var seckill = {
	// 封装秒杀相关ajax的url
	URL : {

		now : function() {
			return '/seckill/seckill/time/now';
		},

		exposer : function(seckillId) {
			return '/seckill/seckill/'+seckillId+'/exposer';
		},
		execution : function(seckillId, md5) {
			return '/seckill/seckill/' + seckillId + '/' + md5 + '/execute';
		}
	},

	// 验证手机号(有可能多个地方都要用到)
	validatePhone : function(phone) {
		if (phone && phone.length == 11 && !isNaN(phone)) {
			return true;
		} else {
			return false;
		}
	},

	handleSeckill : function(seckillId, node) {

		// 获取秒杀地址，控制显示逻辑，执行秒杀
		node
				.hide()
				.html(
						'<button class="btn bg-primary btn-lg" id="killBtn">开始秒杀</button>');// 秒杀按钮
		$.post(seckill.URL.exposer(seckillId), {}, function(result) {
			console.log("dao exposer");
			// 在回调函数中执行交互流程
			if (result && result['success']) {
				var exposer = result['data'];
				if (exposer['exposed']) {
					// 开启秒杀
					var md5 = exposer['md5'];
					//获取秒杀地址
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl"+killUrl);
                    //只绑定一次点击事件
                    $('#killBtn').one('click', function(){
                    	//1.执行秒杀请求
                    	//2.先禁用按钮
                        $(this).addClass('disabled');
                        //发送秒杀请求
                        $.post(killUrl, {}, function(result){
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
				} else {
					// 未开启秒杀
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					seckill.countdown(seckillId, now, start, end);
				}
			} else {
				console.log('result : ' + result);
			}

		});
	},

	// 倒计时判断相关函数封装
	countdown : function(seckillId, nowTime, startTime, endTime) {
		var seckillBox = $('#seckill-box');
		// 判断时间
		if (nowTime > endTime) {
			seckillBox.html('秒杀结束');
		} else if (nowTime < startTime) {
			// 秒杀未开始,计时事件绑定
			// var killTime1 = new Date(startTime+1000);
			var killTime = Number(startTime);
			//console.log("result444=" + killTime);
			seckillBox.countdown(killTime, function(event) {
				// 时间格式
				var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
				seckillBox.html(format);
				// 计时完成后的回调事件
			}).on('finish.countdown', function() {
				// 获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckill(seckillId, seckillBox);
			});

		} else {
			

			// 秒杀进行中
			seckill.handleSeckill(seckillId, seckillBox);
		}
	},

	// 详情页秒杀逻辑
	detail : {
		// 详情页初始化
		init : function(params) {
			// 手机验证登录，计时交互

			// 在cookie中查找手机号
			var killPhone = $.cookie('killPhone');

			// 验证手机号(放在上层验证)
			if (!seckill.validatePhone(killPhone)) {
				var killPhoneModal = $('#killPhoneModal');
				// 显示弹出层
				killPhoneModal.modal({
					show : true, // 显示弹出层
					backdrop : 'static', // 禁止位置关闭
					keyboard : false
				// 关闭键盘事件
				});

				$('#killPhoneBtn')
						.click(
								function() {
									var inputPhone = $('#killphoneKey').val();
									console.log('inputPhone=' + inputPhone);// TODO
									if (seckill.validatePhone(inputPhone)) {
										// 电话写入cookie
										$.cookie('killPhone', inputPhone, {
											expires : 7,
											path : '/seckill/seckill'
										});
										// 刷新页面
										window.location.reload();
									} else {
										$('#killphoneMessage')
												.hide()
												.html(
														'<label class="label label-danger">手机号错误！</label>')
												.show(300);
									}
								});
			}
			// 已经登录
			// 计时交互
			var seckillId = params['seckillId'];
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			$.get(seckill.URL.now(), {}, function(result) {
				if (result && result['success']) {
					console.log("result111=" + result);
					var nowTime = result['data'];
					console.log("result333=" + nowTime);
					seckill.countdown(seckillId, nowTime, startTime, endTime);
				} else {
					console.log("result222=" + result);

				}
			});
		}
	}

}