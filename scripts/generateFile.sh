#!/usr/bin/env bash
echo "Writing $2 MB to $1"
dd if=/dev/urandom of=$1 bs=1048576 count=$2
