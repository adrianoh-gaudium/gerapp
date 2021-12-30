-- Corridas disparadas e finalizadas
select month(s.data_hora_solicitacao), c.nome, c.`telefone`, count(*)
from solicitacao s, cliente c
where s.`bandeira_chamada_id`=44
and s.`cliente_id`=c.id
and s.`status_solicitacao`="F"
and s.`data_hora_solicitacao`>"2015-08-01" 
and s.`data_hora_solicitacao`<"2015-11-01" 
group by month(s.data_hora_solicitacao), c.nome, c.`telefone`
order by month(s.data_hora_solicitacao), c.nome