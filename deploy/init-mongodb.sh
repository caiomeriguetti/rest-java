#!/bin/bash

if [ ! -f ../data ]; then
	mkdir -p ../data
fi

mongod --dbpath ../data --port 27018 --shutdown
mongod --dbpath ../data --port 27018 &