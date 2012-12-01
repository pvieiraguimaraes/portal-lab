insert into caso_de_uso(nome_caso,descricao_caso,status_caso)
select * from (
	select 'UC_SEG_CASODEUSO'		as nome_caso, 'Manter Caso de Usos'			as descricao_caso,1	as status_caso union
	select 'UC_SEG_FUNCIONALIDADE'	as nome_caso, 'Manter Funcionalidades'		as descricao_caso,1	as status_caso union
	select 'UC_SEG_GRUPOUSUARIO'	as nome_caso, 'Manter Grupo de Usuários'	as descricao_caso,1	as status_caso union
	select 'UC_SEG_USUARIO'			as nome_caso, 'Manter Usuários'				as descricao_caso,1	as status_caso  
)caso
where not exists(
	select 1
	from caso_de_uso c
	where c.nome_caso=caso.nome_caso
)

insert into funcionalidade(nome_func,descricao_func,status_func)
select * from (
	select 'FUNC_CREATE'	as nome_func, 'Inclusão'		as descricao_func,1	as status_func union
	select 'FUNC_UPDATE'	as nome_func, 'Alteração'		as descricao_func,1	as status_func union
	select 'FUNC_DELETE'	as nome_func, 'Exclusão'		as descricao_func,1	as status_func union
	select 'FUNC_FIND_ALL'	as nome_func, 'Localizar todos'	as descricao_func,1	as status_func  
)func
where not exists(
	select 1
	from funcionalidade f
	where f.nome_func=func.nome_func
)

insert into casodeuso_funcionalidade(id_caso_cafu,id_func_cafu)
select id_caso,id_func 
from caso_de_uso cu, funcionalidade fu
where		cu.nome_caso in('UC_SEG_CASODEUSO','UC_SEG_FUNCIONALIDADE','UC_SEG_GRUPOUSUARIO','UC_SEG_USUARIO')
		and fu.nome_func in('FUNC_CREATE','FUNC_UPDATE', 'FUNC_DELETE', 'FUNC_FIND_ALL')
		and not exists(
			select 1
			from casodeuso_funcionalidade cafu
			where		cafu.id_caso_cafu = id_caso
					and cafu.id_func_cafu = id_func
		)
		
insert into grupo_de_usuario(nome_usgr,status_usgr)
select 'ROLE_SEG_ADMIN'	as nome_usgr,1	as status_usgr
where not exists(
	select 1
	from grupo_de_usuario gu
	where gu.nome_usgr = 'ROLE_SEG_ADMIN'
)

insert into grupo_permissao(id_grup,id_cafu)
select id_grup,id_cafu from 
(
	select 
		(select id_usgr from grupo_de_usuario where nome_usgr='ROLE_SEG_ADMIN') as id_grup,
		id_cafu
	from casodeuso_funcionalidade cafu
	inner join caso_de_uso cu
		on cu.id_caso = cafu.id_caso_cafu
	inner join funcionalidade fu
		on fu.id_func = cafu.id_func_cafu
	where		cu.nome_caso in('UC_SEG_CASODEUSO','UC_SEG_FUNCIONALIDADE','UC_SEG_GRUPOUSUARIO','UC_SEG_USUARIO')
			and fu.nome_func in('FUNC_CREATE','FUNC_UPDATE', 'FUNC_DELETE', 'FUNC_FIND_ALL')
)grpe
where not exists(
	select 1
	from grupo_permissao p2
	where		p2.id_grup = grpe.id_grup
			and p2.id_cafu = grpe.id_cafu
)

insert into usuarios(login_usua,senha_usua,status_usua,nome_usua)
select 
	'SEGADMIN'			as login_usua,
	'admin'				as senha_usua,
	'Ativo'				as status_usua,
	'Administrador de Segurança'	as nome_usua
where not exists(
	select 1
	from usuarios
	where login_usua='SEGADMIN'
)

insert into usuario_grupo(id_usua_usgr, id_grus_usgr)
select id_usua_usgr, id_grus_usgr
from(
	select
		(select codigo_usua from usuarios where login_usua='SEGADMIN') as id_usua_usgr,
		(select id_usgr from grupo_de_usuario where nome_usgr='ROLE_SEG_ADMIN') as id_grus_usgr
)ug
where not exists(
	select 1 
	from usuario_grupo ug2
	where		ug2.id_usua_usgr = ug.id_usua_usgr
			and ug2.id_grus_usgr = ug2.id_grus_usgr
)
	
	
