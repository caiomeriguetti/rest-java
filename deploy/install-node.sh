#!/bin/bash

source check-tmp.sh

if [ ! -f tmp/node-v4.3.2-linux-x64.tar.xz ]; then
  wget https://nodejs.org/dist/v4.3.2/node-v4.3.2-linux-x64.tar.xz -O tmp/node-v4.3.2-linux-x64.tar.xz
fi

sudo mkdir -p /opt/node

tar -xf tmp/node-v4.3.2-linux-x64.tar.xz -C /opt/node

update-alternatives --install /usr/bin/node node /opt/node/node-v4.3.2-linux-x64/bin/node 2110
update-alternatives --install /usr/bin/npm npm /opt/node/node-v4.3.2-linux-x64/bin/npm 2110
