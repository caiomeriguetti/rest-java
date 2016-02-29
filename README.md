Installing packages:

```bash
cd enter/your/app/dir
/bin/bash install.sh
```

Deploy the ui and REST api:

```bash
cd enter/your/app/dir
/bin/bash deploy.sh
```

The UI is on localhost:7171/ and localhost:7070/  and the REST api endpoint is localhost:8282/api/

To run the Https Reverse proxy:

```bash
cd enter/your/app/dir
cd https-rproxy
mvn exec:java
```
The reverse proxy will handle the requests on port 80 and forward them to the ui servers on ports 7171 and 7070.
