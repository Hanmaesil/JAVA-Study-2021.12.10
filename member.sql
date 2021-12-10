create table bigmember(
	id varchar2(500),
	pw varchar2(500),
	nick varchar2(500)
)

drop table bigmember

insert into bigmember
values('pbk','1234', '호두아빠' );
insert into bigmember
values('lsh', '5678', '승환왕자');
insert into bigmember
values('kdw', '1234', '동원참치');
insert into bigmember
values('csm', '1234', '최수민');


select * from bigmember