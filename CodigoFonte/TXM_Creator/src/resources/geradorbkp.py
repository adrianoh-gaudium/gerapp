#!/usr/env/python
# -*- coding: utf-8 -*-

import os
import subprocess

import Modules.metadata.cadastrar
import Modules.mapa.mapa

import csv

import sys
sys.path.append("Modules/strings")
import removedor

#print "Abrindo janela de escolha de arquivos..."
#print ""
diretorio_imagens="Design"
diretorio_dados="Miscelâneas"
diretorio_mkt="Marketing & Vendas"

pasta_dados_app="/Users/integrador/DadosApp/"
pasta_projetos="/Users/integrador/ProjetosAndamento/"
pasta_android_custom="/Users/integrador/AndroidCustom/"
pasta_ios_custom="/Users/integrador/iOSCustom/"

dadocomm=sys.argv[1:]
#dadocomm.append(False)
projs=[]
opcoes=[]

aguardandoativacao=[]

#Diretorio atual
dir_atual=os.getcwd()

for item in dadocomm:
	if '-' in item:
		opcoes.append(item)
	else:
		projs.append(item)

for projeto in projs:

	if projeto == "076":
		nomefolder=subprocess.check_output("ls "+pasta_projetos+" | grep Proj"+projeto+"-", shell=True)[0:-1]
	else:
		nomefolder=subprocess.check_output("ls "+pasta_projetos+" | grep Proj"+projeto+"-TXM-", shell=True)[0:-1]

	nomedir=pasta_projetos+nomefolder

	miscdados=nomedir+"/"+diretorio_dados
	miscdados=miscdados.replace("\\","")

	if os.path.isfile(nomedir+"/Android") or os.path.isfile(nomedir+"/iOS"):
		os.system("rm -r "+nomedir+"/Android "+nomedir+"/iOS")

	if os.path.isfile(miscdados+"/dados.d"):
		arqdados = open(miscdados+"/dados.d");

	hash_vars={}

	#Inserindo Analytics padrão
	hash_vars["ANALYTICS"]="46053169"

	#Inserindo ID do iTunes vazio para não rodar geração de QR Codes caso não haja o código
	hash_vars['URLITUNES']="none"

	#Inserindo telefone vazio para não rodar a geração do telefone
	hash_vars['TELEFONE']="none"

	#Inserindo idioma padrão
	hash_vars['IDIOMA']="pt-BR"

	#Inserindo bairro padrão
	hash_vars['BAIRRO']="Centro"

	#Obedecendo o bundle Drivermachine por padrão
	hash_vars['DRIVERMACHINE']="YES"

	print ""
	print "Lendo arquivo dados.d da bandeira..."
	print ""
	x=0
	for linha0 in arqdados:
		if not linha0.startswith("//") and not linha0.rstrip()=="":
			if "://" in linha0:
				linha="//".join(linha0.split("//")[0:2]).rstrip()
			else:
				linha=linha0.split("//", 1)[0].rstrip()
			#print linha
			linha_var=linha.rstrip().split(":",1)
			hash_vars[linha_var[0]]=linha_var[1]
			print "O valor de",linha_var[0],"é",linha_var[1]	
	arqdados.close()

	if hash_vars['TIPO'] == 'Motorista' and hash_vars['DRIVERMACHINE'] == "YES":
		hash_vars['MACHINE']='driver'		
	else:
		hash_vars['MACHINE']='taxi'

	if "SITE" not in hash_vars.keys():
		hash_vars['SITE']="http://www."+hash_vars['MACHINE']+"machine.com.br"

	if "MAPKEYTAXISTA" not in hash_vars.keys():
		hash_vars['MAPKEYCLIENTE']=hash_vars['MAPKEYANDROID']
		hash_vars['MAPKEYTAXISTA']=hash_vars['MAPKEYANDROID']

	if "NOMEAPPPASSAGEIRO" not in hash_vars.keys():
		hash_vars['NOMEAPPPASSAGEIRO']=hash_vars['NOMEAPP']

	if "NOMEAPPTAXISTA" not in hash_vars.keys():
		hash_vars['NOMEAPPTAXISTA']=hash_vars['TIPO']+" "+hash_vars['NOMEAPP']

	if "BUNDLEID" not in hash_vars.keys():
		hash_vars['BUNDLEID']="br.com."+hash_vars['MACHINE']+"machine."+hash_vars['NOMECOOP'].lower()

	if "APPSTAXIBR" not in hash_vars.keys():
		hash_vars['APPSTAXIBR']=removedor.remover_acentos(hash_vars['NOMEAPP']).lower().replace(" ","").replace("\'","")

	if "ITUNESAPP" not in hash_vars.keys():
		hash_vars['ITUNESAPP']=hash_vars['NOMEAPP']
	hash_vars['ITUNESAPP']=hash_vars['ITUNESAPP'].lower().replace(" ","-").replace("\'","")

	if "URLITUNES" not in hash_vars.keys():
		hash_vars['URLITUNES']="https://itunes.apple.com/br/app/"+hash_vars['ITUNESAPP']+"/id"+hash_vars['ITUNESID']+"?l=pt&ls=1&mt=8\napps.taxi.br"
	else:
		hash_vars['URLITUNES']=hash_vars['URLITUNES'].replace("/us/","/br/")

	hash_vars['ASCIINOMEAPP']=removedor.remover_acentos(hash_vars['NOMEAPP']).replace("\'","")

	hash_vars['SITE']=hash_vars['SITE'].replace('/','\/')

	if opcoes==[] or '-pkg' in opcoes:
		os.system("cp -R modules/arquivos-base/Android modules/arquivos-base/iOS "+nomedir)

		arquivos={
		"APPKEY":[
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/raw/url.properties",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/raw/url.properties",
		"iOS/coopios/TaxiMachine/Config/Config.h"
		],
		"NOMEAPPPASSAGEIRO":[
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values/custom.xml",
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values-es/custom.xml",
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values-pt/custom.xml",
		#"Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml",
		#"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml",
		#"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml",
		"iOS/coopios/TaxiMachine/TaxiMachine-Info.plist"
		],
		"NOMEAPPTAXISTA":[
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml"
		],
		"SITE":[
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values/custom.xml",
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values-es/custom.xml",
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values-pt/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml",
		"iOS/coopios/TaxiMachine/Config/Config.h"
		],
		"EMAIL":[
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values/custom.xml",
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values-es/custom.xml",
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values-pt/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-es/custom.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values-pt/custom.xml",
		"iOS/coopios/TaxiMachine/Config/Config.h"
		],
		"PROJECTNUM":[
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values/gcmdata.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values/gcmdata.xml"
		],
		"ANALYTICS":[
		"Android/CoopAndroid/coop-modelo_cliente_custom/res/values/analytics.xml",
		"Android/CoopAndroid/coop-modelo_taxista_custom/res/values/analytics.xml",
		"iOS/coopios/TaxiMachine/Config/Config.h"
		],
		"MAPKEYCLIENTE":["Android/CoopAndroid/coop-modelo_cliente_custom/res/values/map_key.xml"
		],
		"MAPKEYTAXISTA":["Android/CoopAndroid/coop-modelo_taxista_custom/res/values/map_key.xml"
		],
		"MAPKEYIOS":[
		"iOS/coopios/TaxiMachine/Config/Config.h"
		],
		"BUNDLEID":[
		"iOS/coopios/TaxiMachine/TaxiMachine-Info.plist"
		],
		}

		print ""
		print "Adicionando dados da bandeira aos arquivos-exemplo..."
		print ""

		#App Key

		for palavra in arquivos.keys():
			#print '============ SE LIGA =====================>',hash_vars[palavra]
			for endereco in arquivos[palavra]:
				#os.system('echo '+hash_vars[palavra].replace('&','\&').replace('\'','\\\'').replace('/','\/'))
				os.system("cat "+nomedir+"/"+endereco+" | sed s/\'_"+palavra+"_\'/"+hash_vars[palavra].replace('&','\&').replace('\'','\\\'').replace('/','\/').replace(' ','\ ')+"/g > temp.txt; cp temp.txt "+nomedir+"/"+endereco)
				print "Alterado item "+palavra+" em arquivo "+nomedir+"/"+endereco

		os.system("rm temp.txt")

	if '-mkt' in opcoes:
		soMarketing='mkt'
	else:
		soMarketing='pkg'

	if not '-meta' in opcoes:
		os.system("sh Modules/imagens/imagens.sh "+nomedir+" "+diretorio_imagens+" "+hash_vars["NOMECOOP"].lower()+" "+hash_vars['URLITUNES'].replace('&','\&')+" "+hash_vars["APPSTAXIBR"]+" "+hash_vars["MACHINE"]+" "+soMarketing+" "+hash_vars['NOMEAPP'].replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+"\ -\ "+hash_vars['CIDADE'].replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+",\ "+hash_vars['UF']+"")
	
		Modules.mapa.mapa.geradorMapas(hash_vars["TIPO"],hash_vars["BAIRRO"],hash_vars["CIDADE"],hash_vars["UF"],nomedir)

	if (opcoes==[] or '-mkt' in opcoes) and not '-no-mkt' in opcoes and not '-reload-meta' in opcoes:

		os.system("sh Modules/qrcodes/qrcodes.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars["NOMECOOP"].lower()+" "+hash_vars['URLITUNES'].replace('&','\&')+" "+hash_vars["APPSTAXIBR"]+" "+hash_vars["MACHINE"]+"")

		os.system("sh Modules/flyer/flyer.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars["TIPO"]+" "+hash_vars["KEYWORD"].upper().replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars['NOMEAPP'].replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars["NOMECOOP"].lower()+" "+hash_vars["BAIRRO"].replace(" ","\ ").replace('&','\&')+" "+hash_vars["CIDADE"].replace(" ","\ ").replace('&','\&')+" "+hash_vars["UF"].upper()+" "+hash_vars["APPSTAXIBR"]+"")

		os.system("sh Modules/appstaxibr/appstaxibr.sh "+nomedir+" "+hash_vars["TIPO"]+"")

		os.system("sh Modules/cartao/cartao.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars["TIPO"]+" "+hash_vars['APPSTAXIBR']+" "+hash_vars["TELEFONE"].replace(" ","\ ").replace('(','\(').replace(')','\)'))

		os.system("sh Modules/adesivo/adesivo.sh "+nomedir+" "+diretorio_mkt.replace(" ","\ ").replace('&','\&')+" "+hash_vars['APPSTAXIBR']+" "+hash_vars["TELEFONE"].replace(" ","\ ").replace('(','\(').replace(')','\)'))

	if '-meta' in opcoes or '-reload-meta' in opcoes:

		os.system("sh Modules/telas/telas.sh "+nomedir+" "+diretorio_imagens+" "+hash_vars['TIPO']+"")

		os.system("sh Modules/metadata/metadata.sh "+nomedir+" "+diretorio_imagens+" "+diretorio_dados+" "+hash_vars['NOMECOOP'].lower()+" "+hash_vars['NOMEAPP'].replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars["KEYWORD"].lower().replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars["TIPO"]+" "+hash_vars["CIDADE"].replace(" ","\ ").replace('&','\&')+" "+hash_vars["IDIOMA"]+" "+hash_vars["BUNDLEID"]+" "+hash_vars['DRIVERMACHINE']+"")
		#os.system("sh Modules/metadata/metadata2.sh "+nomedir+" "+diretorio_imagens+" "+diretorio_dados+" "+hash_vars['NOMECOOP'].lower()+" "+hash_vars['NOMEAPP'].replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars["KEYWORD"].lower().replace(" ","\ ").replace("\'","\\\'").replace('&','\&')+" "+hash_vars["TIPO"]+" "+hash_vars["CIDADE"].replace(" ","\ ").replace('&','\&')+" "+hash_vars["IDIOMA"]+" "+hash_vars["BUNDLEID"]+" "+hash_vars['DRIVERMACHINE']+"")


		exigeativ=Modules.metadata.cadastrar.cliente(hash_vars['APPKEY'])
		if str(exigeativ) == "1":
			aguardandoativacao.append(hash_vars["NOMECOOP"])

	if opcoes==[] or '-pkg' in opcoes:

		if not '-no-certs' in opcoes:
			os.system("sh Modules/certsechaves/geraCertseChaves.sh "+nomedir+" "+hash_vars['NOMECOOP']+" "+hash_vars["MACHINE"]+" "+diretorio_dados+" "+hash_vars["BUNDLEID"]+"")

		print "Renomeando pastas..."
		os.chdir(nomedir.replace('\\','')+"/Android/CoopAndroid")

		os.system("mv coop-modelo_cliente_custom "+hash_vars["NOMECOOP"].lower()+"_cliente_custom")
		os.system("mv coop-modelo_taxista_custom "+hash_vars["NOMECOOP"].lower()+"_taxista_custom")

		os.system("echo tipo="+hash_vars["MACHINE"].lower()+" > bandeira.properties")

		os.chdir(nomedir.replace('\\','')+"/Android")
		os.system("mv CoopAndroid "+hash_vars["NOMECOOP"])
		os.system("cp -R "+hash_vars["NOMECOOP"]+" "+pasta_dados_app+"/android")
		os.system("cp -R "+hash_vars["NOMECOOP"]+" "+pasta_android_custom)

		os.chdir(nomedir.replace('\\','')+"/iOS")
		os.system("mv coopios "+hash_vars["NOMECOOP"].lower())
		os.system("cp -R "+hash_vars["NOMECOOP"].lower()+" "+pasta_dados_app+"/iOS")
		os.system("cp -R "+hash_vars["NOMECOOP"].lower()+" "+pasta_ios_custom)


	if opcoes==[] or '-mkt' in opcoes:

		os.chdir(nomedir.replace('\\','')+"/"+diretorio_mkt)
		print "Compactando..."
		os.system("zip -r "+hash_vars["NOMECOOP"]+"-Marketing.zip Adesivo/adesivo.png Cartao/cartao.png Flyer/flyer.png QRCodes/qrcode-*.png")

	os.chdir(dir_atual)

print ""
if aguardandoativacao != []:
	print "========================================"
	print "================ATENÇÃO================="
	print "========================================"
	print "== A(s) bandeira(s) abaixo estão com o ="
	print "== Cliente Teste aguardando ativação: =="
	print ""
	for itemlista in aguardandoativacao:
		print itemlista 
print ""
print "========================================"
print "================ FIM ==================="