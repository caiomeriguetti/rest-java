#!/bin/bash

pythonVersion=$(python --version 2>&1)                                                                                                                    
installed=0
if [ "$pythonVersion" == "Python 2.7.11" ]; then
	echo "Python already installed"
	installed=1
fi

if [ installed == 0 ]; then
	if [ ! -f Python-2.7.11.tar.xz ]; then
	  wget https://www.python.org/ftp/python/2.7.11/Python-2.7.11.tar.xz
	fi

	sudo apt-get install -y build-essential checkinstall
	sudo apt-get install -y libreadline-gplv2-dev libncursesw5-dev libssl-dev libsqlite3-dev tk-dev libgdbm-dev libc6-dev libbz2-dev
	tar -xf Python-2.7.11.tar.xz
	cd Python-2.7.11
	./configure
	make
	sudo checkinstall
	wget https://bootstrap.pypa.io/get-pip.py
	sudo python get-pip.py
	update-alternatives --install /usr/local/bin/python java /usr/local/bin/python2.7 2110
fi

sudo python -m pip install beautifulsoup4

