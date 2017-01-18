--���ݿ��ʼ���ű�
--�������ݿ�
create database seckill;
--ʹ�����ݿ�
use seckill;
--������ɱ����
create table seckill(
seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���id',
name varchar(120) NOT NULL  COMMENT '��Ʒ����',
number int NOT NULL  COMMENT '��Ʒ���',
start_time timestamp   NULL  COMMENT '��ɱ��ʼʱ��',
end_time timestamp  NULL  COMMENT '��ɱ����ʱ��',
create_time timestamp NOT NULL DEFAULT current_timestamp COMMENT '����ʱ��',

 PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)

)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='��ɱ����';

--��ʼ������
insert into seckill(name,number,start_time,end_time)
values ('1000Ԫ��ɱiphone6',100,'2016-12-30 00:00:00','2016-12-31 00:00:00'),
       ('100Ԫ��ɱipad2',200,'2016-12-30 00:00:00','2016-12-31 00:00:00'),
       ('400Ԫ��ɱС��5',300,'2016-12-30 00:00:00','2016-12-31 00:00:00'),
       ('600Ԫ��ɱ����note',400,'2016-12-30 00:00:00','2016-12-31 00:00:00');
       
--��ɱ�ɹ���ϸ��
create table success_kill(
seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '��ɱ��Ʒid',
user_phone bigint not null COMMENT '�û��ֻ���',
state tinyint not null default -1 comment '״̬:-1:��Ч 0:�ɹ� 1:�Ѹ��� 2:�ѷ���',
create_time timestamp NOT NULL DEFAULT current_timestamp COMMENT '����ʱ��',

PRIMARY KEY (seckill_id,user_phone),
  KEY idx_create_time(create_time)

)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='��ɱ�ɹ���ϸ��';  
       
       

--�������ݿ����̨
mysql -uroot -p150150
