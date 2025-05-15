#!/bin/sh

HOST_PORT=$1
HOST=$(echo "$HOST_PORT" | cut -d: -f1)
PORT=$(echo "$HOST_PORT" | cut -d: -f2)

shift

echo "Aguardando por $HOST:$PORT..."
until nc -z "$HOST" "$PORT"; do
  echo "Ainda não disponível - $HOST:$PORT"
  sleep 2
done

echo "$HOST:$PORT está disponível, a arrancar o serviço..."
exec "$@"
