#!/bin/bash

pasta_design="$1/$2"
mkdir -p "$pasta_design"/Telas
# Inicio - Horácio - 15/07/2018
#pasta_origem=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/Modules/telas"
pasta_origem="$4"/telas
# Fim - Horácio - 15/07/2018
cd "$pasta_origem"

case $3 in
	"Taxista" )
	tipo="taxi"
	cor="Yellow"
	;;
	"Motorista" )
	tipo="executivo"
	cor="Blue"
	;;
	"Mototaxista" )
	tipo="mototaxi"
	cor="Yellow"
	;;
esac

mkdir -p "$pasta_design"/Telas/Taxista "$pasta_design"/Telas/iOS "$pasta_design"/Telas/Android

mv  "$pasta_design"/Telas/iOS/* "$pasta_design"/Telas/Backup
mv  "$pasta_design"/Telas/taxista/* "$pasta_design"/Telas/Backup
mv  "$pasta_design"/Telas/Android/* "$pasta_design"/Telas/Backup

# cp taxista/* "$pasta_design"/Telas/Taxista
cp taxista/prontas/"$tipo"/* "$pasta_design"/Telas/taxista/;

echo "Gerando telas para a loja..."
for tamanho in 55 65; do
#for tamanho in 3 4; do
	#if [ $tamanho == 3 ]; then tam2=35; else tam2=4; fi
	tam2=$tamanho

	for i in 2 4 5; do
		cp ios/"$tamanho"/prontas/tela"$i"-"$tipo".png "$pasta_design"/Telas/iOS/"$i"_iphone"$tam2"_"$i"."$tamanho"-"$i".png;
		# Retirando o android pois voltou a ter telas fixas e personalizadas
		# cp android/"$tamanho"/prontas/tela"$i"-"$tipo".png "$pasta_design"/Telas/Android/"$i"_android"$tam2"_"$i"."$tamanho"-"$i".png;
	done;

	# Copiando as novas telas fixas e personalizadas
	cp android/"$tamanho"/prontas/"$tipo"/* "$pasta_design"/Telas/Android/;


	for mapa in 1 3; do
		mapanome="";
		bottom="$tipo";
		middlebar=0;

		if [ $mapa == 3 ]; then
			mapanome="rotacliente"; bottom="info"; middlebar=true;
		fi;

		# CREATE Android IMAGES

		imgOutput="$pasta_design"/imagensGeradas/"$mapa"_android.png
		#Customização do android
		echo convert -size 426x760 xc:snow -colorspace sRGB "$imgOutput"
		convert convert -size 426x760 xc:snow -colorspace sRGB "$imgOutput"

		# convert "$pasta_design"/imagensGeradas/mapa"$mapanome"_reskin.png -resize 426x652! "$pasta_design"/imagensGeradas/mapa"$mapanome"_reskin.png
		# composite -geometry +0+46 "$pasta_design"/imagensGeradas/mapa"$mapanome"_reskin.png "$imgOutput" "$imgOutput"
		convert "$pasta_design"/imagensGeradas/mapa"$mapanome"_reskin.png -resize 426x614^ "$pasta_design"/imagensGeradas/mapa"$mapanome"_reskin.png
		composite -geometry +0+84 "$pasta_design"/imagensGeradas/mapa"$mapanome"_reskin.png "$imgOutput" "$imgOutput"
		composite -geometry +0+0 android/"$tamanho"/Tela_"$mapa"_"$cor".png "$imgOutput" "$imgOutput"
		composite -geometry +0+45 "$pasta_design"/imagensGeradas/coop_name.png "$imgOutput" "$imgOutput"

		if [ $mapa == 1 ]; then
			echo composite -geometry +30+154 "$pasta_design"/imagensGeradas/address_name.png "$imgOutput" "$imgOutput"
			composite -geometry +30+156 "$pasta_design"/imagensGeradas/address_name.png "$imgOutput" "$imgOutput"
			composite -geometry +160+369 "$imgOutput" "$pasta_design"/Telas/Android/"$mapa".png "$pasta_design"/Telas/Android/"$mapa".png
		else
			composite -geometry +149+369 "$imgOutput" "$pasta_design"/Telas/Android/"$mapa".png "$pasta_design"/Telas/Android/"$mapa".png
		fi;

			# cp "$pasta_design"/imagensGeradas/mapa"$mapanome".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			#convert "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity Center -crop 720x933+0+0 +repage "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			#convert "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity North -background grey -splice 0x147 "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			#convert "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity South -background grey -splice 0x200 "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
		
			#if [ $tamanho == 3 ]; then 
			#	convert "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity Center -crop 720x933+0+0 +repage "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			#fi;
			
			# composite -geometry +0+0 android/"$tamanho"/topo1.png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			# composite -geometry +229+51 "$pasta_design"/imagensGeradas/262x92fa.png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;

			# if [ $middlebar == true ]; then
			# 	composite -geometry +0+147 android/"$tamanho"/middle-"$tipo".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			# fi

			# composite -geometry +0+0 android/"$tamanho"/bottom-"$bottom".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/Android/"$mapa"_android"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
		#Fim da customização do android

		# CREATE iOS IMAGES

		cp "$pasta_design"/imagensGeradas/mapa"$mapanome".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;

		#sips -Z 910 "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png

		if [ $tamanho == 55 ]; then
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity Center -crop 640x911+0+0 +repage "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity North -background grey -splice 0x128 "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity South -background grey -splice 0x98 "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			logoy=46
		elif [ $tamanho == 65 ]; then
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity Center -crop 640x1391+0+0 +repage "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity North -background grey -splice 0x128 "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity South -background grey -splice 0x98 "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
			logoy=74
		fi
		
	
		if [ $tamanho == 3 ]; then 
			convert "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png -gravity Center -crop 640x960+0+0 +repage "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
		fi;
		
		composite -geometry +0+0 ios/"$tamanho"/topo1.png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
		composite -geometry +212+"$logoy" "$pasta_design"/imagensGeradas/216x76fa.png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;

		if [ $middlebar == true ]; then
			composite -geometry +0+0 ios/"$tamanho"/middle-"$tipo".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;
		fi

		composite -gravity South ios/"$tamanho"/bottom-"$bottom".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png "$pasta_design"/Telas/iOS/"$mapa"_iphone"$tam2"_"$mapa"."$tamanho"-"$mapa".png;

	done;
done

sips -z 2688 1242 "$pasta_design"/Telas/iOS/*65*.png

sips -z 2208 1242 "$pasta_design"/Telas/iOS/*55*.png

#sips -z 1136 640 "$pasta_design"/Telas/iOS/1_iphone4_1.4-1.png
#sips -z 1136 640 "$pasta_design"/Telas/iOS/3_iphone4_3.4-3.png

# Inicio - Horácio - 15/07/2018
cd -
# Fim - Horácio - 15/07/2018

