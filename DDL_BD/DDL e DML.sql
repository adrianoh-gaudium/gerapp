-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           5.6.44-log - MySQL Community Server (GPL)
-- OS do Servidor:               Linux
-- HeidiSQL Versão:              11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para desenv
CREATE DATABASE IF NOT EXISTS `desenv` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `desenv`;

-- Copiando estrutura para tabela desenv.appcreator_bandeira_cfg
CREATE TABLE IF NOT EXISTS `appcreator_bandeira_cfg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Chave primária',
  `bandeira_id` int(11) NOT NULL DEFAULT '0' COMMENT 'FK de bandeira',
  `chave` varchar(20) NOT NULL,
  `valor` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bandeira_cfg_ibfk_1` (`bandeira_id`),
  CONSTRAINT `bandeira_cfg_ibfk_1` FOREIGN KEY (`bandeira_id`) REFERENCES `bandeira` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='Informações de configuração para cada bandeira, usadas no aplicativo de construção dos aplicativos android e iOS.';

-- Copiando dados para a tabela desenv.appcreator_bandeira_cfg: ~36 rows (aproximadamente)
/*!40000 ALTER TABLE `appcreator_bandeira_cfg` DISABLE KEYS */;
INSERT INTO `appcreator_bandeira_cfg` (`id`, `bandeira_id`, `chave`, `valor`) VALUES
	(1, 966, 'DRIVERMACHINE', 'YES'),
	(2, 966, 'ANALYTICS', NULL),
	(3, 966, 'URLITUNES', 'https://apps.apple.com/br/app/novo-passageiro/id1537659047?l=pt&ls=1&mt=8'),
	(4, 966, 'ITUNESAPP', NULL),
	(5, 966, 'TELEFONE', '(015) 2258-0904'),
	(6, 966, 'IDIOMA', 'pt-BR'),
	(7, 966, 'BAIRRO', 'Canudos'),
	(8, 966, 'APPSTAXIBR', 'novo'),
	(9, 966, 'NOMEAPPTAXISTA', NULL),
	(10, 966, 'LOGINCPF', NULL),
	(11, 966, 'NOMEAPPPASSAGEIRO', 'NOVO - Passageiro'),
	(12, 966, 'MAPKEYCLIENTE', NULL),
	(13, 966, 'MAPKEYTAXISTA', NULL),
	(14, 966, 'SITE', 'http://novo.com.br'),
	(15, 966, 'BUNDLEID', NULL),
	(16, 966, 'ACCKITKEY', 'deprecated'),
	(17, 966, 'APPKEY', '"chNOVO-H0UitVN"'),
	(18, 966, 'TIPO', 'Motorista'),
	(19, 966, 'PLANO', 'Pro'),
	(20, 966, 'NOMEAPP', 'NOVO'),
	(21, 966, 'KEYWORD', 'NOVO'),
	(22, 966, 'CIDADE', 'Novo Hamburgo'),
	(23, 966, 'UF', 'RS'),
	(24, 966, 'EMAIL', 'horacio73@gmail.com'),
	(25, 966, 'NOMECOOP', 'NOVO1'),
	(26, 966, 'PROJECTNUM', '909090909'),
	(27, 966, 'MAPKEYANDROID', 'AlzAndroid123'),
	(28, 966, 'MAPKEYIOS', 'AlzApple123'),
	(29, 966, 'FIREBASEPASSAGEIRO', 'ab18ed13d13123e9c5d9cf'),
	(30, 966, 'FIREBASEPASSAGEIROPR', '1071465414494'),
	(31, 966, 'FIREBASETAXISTAPROJ', '1071465414494'),
	(32, 966, 'FIREBASETAXISTA', '62da70e17425b369c5d9cf'),
	(33, 966, 'FACEBOOKPASSAGEIRO', '269924647455359'),
	(34, 966, 'USUARIOITUNES', 'gaudiumtecnicos@gmail.com'),
	(35, 966, 'ITUNESTEAMNAME', 'NOVO TECNOLOGIA LTDA'),
	(36, 966, 'ITUNESTEAMID', '78ADRS7VJ3');
/*!40000 ALTER TABLE `appcreator_bandeira_cfg` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
