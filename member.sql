create table bigmember(
	id varchar2(500),
	pw varchar2(500),
	nick varchar2(500)
)

drop table bigmember

insert into bigmember
values('pbk','1234', 'ȣ�ξƺ�' );
insert into bigmember
values('lsh', '5678', '��ȯ����');
insert into bigmember
values('kdw', '1234', '������ġ');
insert into bigmember
values('csm', '1234', '�ּ���');


select * from bigmember