#!/bin/bash

if [ ! -f node-v4.3.2-linux-x64.tar.xz ]; then
  wget https://nodejs.org/dist/v4.3.2/node-v4.3.2-linux-x64.tar.xz
fi

sudo mkdir /opt/node

tar -xf node-v4.3.2-linux-x64.tar.xz -C /opt/node

update-alternatives --install /usr/bin/node node /opt/node/node-v4.3.2-linux-x64/bin/node 2110
update-alternatives --install /usr/bin/npm npm /opt/node/node-v4.3.2-linux-x64/bin/npm 2110