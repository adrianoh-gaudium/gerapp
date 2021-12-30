#!/usr/env/python
# -*- coding: utf-8 -*-

import sys
import os
import random
import json
import urllib, urllib2
import math

def geradorMapas(tipobandeira, bairro, cidade, uf, enderecobandeira):
	print "Gerando mapas..."

	#inp = sys.argv
	#print inp
	local_padrao=bairro+' '+cidade+' '+uf

	if tipobandeira == 'Taxista':
		livre='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-livre.png'
		ocupado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-ocupado.png'
		confirmado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-taxi-confirmado.png'
	elif tipobandeira == 'Mototaxista':
		livre='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-livre.png'
		ocupado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-ocupado.png'
		confirmado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-moto-confirmado.png'
	elif tipobandeira == 'Motorista':
		livre='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-livre.png'
		ocupado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-ocupado.png'
		confirmado='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-carro-confirmado.png'

	bola='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-lugar.png'
	passageiro='http://www.taximachine.com.br/wp-content/uploads/2016/03/pin-pass.png'

	deltalat=0.017813
	deltalng=0.013381

	#pegar latlong do local
	print "Obtendo latlong do local"
	local = urllib.quote(local_padrao)
	dadocidade=urllib2.urlopen('https://maps.googleapis.com/maps/api/geocode/json?address='+local)

	objcidade=json.loads(dadocidade.read().decode('utf-8'))

	dadocidade.close()

	centrolat=objcidade['results'][0]['geometry']['location']['lat']
	centrolng=objcidade['results'][0]['geometry']['location']['lng']

	pareslatlong={}
	pareslatlong['lat']=[]
	pareslatlong['lng']=[]
	objrota={}
	objrota['status']=""
	parrota=[]


	totaldetaxistas=8
	#desvlng=0
	#desvlat=0
	maxtries=5
	divdelta=2.5

	#def desvpad(lista):
	#	soma=sum(lista)
	#	somaquadrados=sum([i**2 for i in lista])
	#	tamanho=float(len(lista))
	#	return math.sqrt(somaquadrados/tamanho-(soma/tamanho)**2)

	for numero in range(1,totaldetaxistas):
		print "Buscando posição de taxista No. "+str(numero)
		flag=True
		counter=0

		while flag and counter<=maxtries:
			randlat=random.uniform(centrolat-deltalat, centrolat+deltalat)
			randlng=random.uniform(centrolng-deltalng, centrolng+deltalng)
			print "Tentativa", counter
			dado=urllib2.urlopen('http://maps.googleapis.com/maps/api/geocode/json?latlng='+str(randlat)+','+str(randlng))

			objeto=json.loads(dado.read().decode('utf-8'))

			if objeto['status'] == 'OK':
				if numero != 1:
					#calculando desvio padrão dos pares latlong existentes
					#desvlat=desvpad(pareslatlong['lat']+[objeto['results'][0]['geometry']['location']['lat']])
					#desvlng=desvpad(pareslatlong['lng']+[objeto['results'][0]['geometry']['location']['lng']])

					#comparando distância ponto a ponto
					enoughdistance = True
					for numerolocal in range(0,len(pareslatlong['lat'])):
						distanciapontos=math.sqrt((pareslatlong['lat'][numerolocal]-objeto['results'][0]['geometry']['location']['lat'])**2+(pareslatlong['lng'][numerolocal]-objeto['results'][0]['geometry']['location']['lng'])**2)
						print "DISTÂNCIA:", distanciapontos
						if distanciapontos < 0.006:
							enoughdistance = False
							
				if numero == 1 or counter == maxtries or enoughdistance:
				#if numero == 1 or counter == maxtries or (desvlat>(deltalat/divdelta) and desvlng>(deltalng/divdelta)):
					print "Posição válida"
					pareslatlong['lat'].append(objeto['results'][0]['geometry']['location']['lat'])
					pareslatlong['lng'].append(objeto['results'][0]['geometry']['location']['lng'])
					flag=False
				
					countrota=0

					while countrota < numero-1 and objrota['status'] != 'OK':
						rota=urllib2.urlopen('https://maps.googleapis.com/maps/api/directions/json?origin='+str(pareslatlong['lat'][countrota])+','+str(pareslatlong['lng'][countrota])+'&destination='+str(pareslatlong['lat'][numero-1])+','+str(pareslatlong['lng'][numero-1])+'&key=AIzaSyD1-Uni1N6FA9DWy4V8zYWOZOGNRJVGyp4')
						objrota=json.loads(rota.read().decode('utf-8'))
						rota.close()
						if objrota['status'] == 'OK':
							print "Rota entre marcadores",countrota+1,"e",numero,"encontrada."
							parrota=[str(pareslatlong['lat'][countrota])+','+str(pareslatlong['lng'][countrota]),str(pareslatlong['lat'][numero-1])+','+str(pareslatlong['lng'][numero-1])]
						countrota=countrota+1

			counter=counter+1
	linhalivre=''

	pares2=zip(pareslatlong['lat'],pareslatlong['lng'])
	for item in pares2[:-2]:
		linhalivre=linhalivre+"%7C"+str(item[0])+','+str(item[1])

	linhaocupado="%7C"+str(pares2[-2][0])+','+str(pares2[-2][1])+"%7C"+str(pares2[-1][0])+','+str(pares2[-1][1])


	os.system('wget -O Modules/mapa/imagens/mapa.png "https://maps.googleapis.com/maps/api/staticmap?center='+str(centrolat)+','+str(centrolng)+'&size=391x531&markers=icon:'+livre+linhalivre+'&markers=icon:'+ocupado+linhaocupado+'&scale=4&zoom=14&amp&format=png&key=AIzaSyD1-Uni1N6FA9DWy4V8zYWOZOGNRJVGyp4"')

	#rota para screenshot

	os.system('wget -O Modules/mapa/imagens/maparotacliente.png "https://maps.googleapis.com/maps/api/staticmap?size=391x531&markers=icon:'+confirmado+'%7C'+parrota[0]+'&markers=icon:'+bola+'%7C'+parrota[1]+'&path=weight:3%7Ccolor:0x0000fe%7Cenc:'+objrota['routes'][0]['overview_polyline']['points'].replace('`','\`')+'&scale=4&zoom=14&amp&format=png&key=AIzaSyD1-Uni1N6FA9DWy4V8zYWOZOGNRJVGyp4"')

	os.system('wget -O Modules/mapa/imagens/maparotataxista.png "https://maps.googleapis.com/maps/api/staticmap?size=391x531&markers=icon:'+bola+'%7C'+parrota[0]+'&markers=icon:'+passageiro+'%7C'+parrota[1]+'&path=weight:3%7Ccolor:0x0000fe%7Cenc:'+objrota['routes'][0]['overview_polyline']['points'].replace('`','\`')+'&scale=4&zoom=14&amp&format=png&key=AIzaSyD1-Uni1N6FA9DWy4V8zYWOZOGNRJVGyp4"')

	os.system('cp Modules/mapa/imagens/mapa*.png '+enderecobandeira+'/Design/imagensGeradas/')
