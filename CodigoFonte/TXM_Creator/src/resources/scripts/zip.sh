#!/bin/bash
dirMkt="$1"
nomecoop="$2"

# Mudando o diretório para realizar a compressão
cd "$dirMkt"

Echo "Compactando..."
zip -r "$nomecoop"-Marketing.zip Adesivo/adesivo.png Cartao/cartao.png Flyer/flyer.png QRCodes/qrcode-*.png

# Retornando para o diretório anterior
cd -