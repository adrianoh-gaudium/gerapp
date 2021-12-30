select c.id, c.nome, c.`email`, c.`telefone`, c.`quantidade_corridas_finalizadas`, c.`data_hora_status_cliente`
from cliente c
left join ( select distinct s.`cliente_id`
			from solicitacao s
			where s.`bandeira_chamada_id` = 44
			and s.`data_hora_solicitacao` > "2019-06-01") b on (b.cliente_id=c.id)
where c.`bandeira_id` =  44
and c.`status_cliente` = "A"
and c.`quantidade_corridas_finalizadas`>0
and b.cliente_id is null;

