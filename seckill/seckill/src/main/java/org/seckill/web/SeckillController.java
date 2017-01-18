/**
 * 
 */
package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *@description:
 *@author MaYue 2017年1月2日下午4:50:25
 *
 */
@Controller//作用是类似与@Service、@Component 将这个类放入到Spring中去
@RequestMapping("/seckill")//url:模块/资源/{id}/细分
public class SeckillController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model)
	{
		//list.jsp+model=ModelAndView
		//获取列表页
		List<Seckill> list=seckillService.getSeckillList();
		//System.out.println("-=-=-=-="+list);
		model.addAttribute("list", list);
		return "list";
	}
	
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId") long seckillId,Model model)
	{
		
		if(String.valueOf(seckillId)==null)
		{
			return "redirect:/seckill/list";
		}
		Seckill seckill=seckillService.getById(seckillId);
		if(seckill==null)
		{
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	@RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")long seckillId)
	{
		SeckillResult<Exposer> result;
		try {
			Exposer exposer=seckillService.exportSeckillUrl(seckillId);
			result=new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result=new SeckillResult<Exposer>(false, e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/{seckillId}/{md5}/exposer",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId,@CookieValue(value="killPhone",required=false)long userPhone,@PathVariable("md5")String md5)
	{
		
		if(String.valueOf(userPhone)==null)
		{
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution=seckillService.excuteSeckill(seckillId, userPhone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);

		} catch (RepeatKillException e) {
			SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
		    return new SeckillResult<SeckillExecution>(false, execution);
		}catch (SeckillCloseException e) {
			SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.END);
		    return new SeckillResult<SeckillExecution>(false, execution);
	
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		    return new SeckillResult<SeckillExecution>(false, execution);
	
		}
			
	}
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time()
	{
		Date now=new Date();
		return new SeckillResult(true,now.getTime());
	}

}