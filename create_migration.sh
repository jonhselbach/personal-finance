#!/bin/bash

# Exemplo: ./create_migration.sh criar tabela categoria

if [ -z "$1" ]; then
  echo "❌ Informe um nome para a migração"
  echo "Exemplo: ./create_migration.sh criar tabela categoria"
  exit 1
fi

# Junta tudo e formata o nome
NAME="$*"
FORMATTED_NAME=$(echo "$NAME" | tr '[:upper:]' '[:lower:]' | tr ' ' '_')

# Data/hora no formato AAAAMMDD_HHMM
TIMESTAMP=$(date +"%Y%m%d_%H%M")

# Caminho fixo para a raiz do projeto, independentemente de onde o script é executado
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
MIGRATIONS_DIR="${PROJECT_ROOT}/src/main/resources/db/migration"

# Nome final do arquivo
FILENAME="${MIGRATIONS_DIR}/V${TIMESTAMP}__${FORMATTED_NAME}.sql"

# Cria o arquivo
mkdir -p "$MIGRATIONS_DIR"
touch "$FILENAME"

# Escreve cabeçalho
{
  echo "-- Migration: $NAME"
  echo "-- Criado em: $(date)"
  echo ""
} > "$FILENAME"

echo "✅ Criado: $FILENAME"
