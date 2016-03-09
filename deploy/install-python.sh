#!/bin/bash

source check-tmp.sh

wget https://bootstrap.pypa.io/get-pip.py -O tmp/get-pip.py
sudo python tmp/get-pip.py
sudo python -m pip install beautifulsoup4
