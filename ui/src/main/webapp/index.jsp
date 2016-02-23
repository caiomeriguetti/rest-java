<html>
  <head>
    <title>Servers Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <base href="/" />
    <link rel="stylesheet" href="styles/global.css" />
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />

    <script src="node_modules/jquery/dist/jquery.min.js"></script>
    <script src="node_modules/es6-shim/es6-shim.min.js"></script>
    <script src="node_modules/systemjs/dist/system-polyfills.js"></script>

    <script src="node_modules/angular2/bundles/angular2-polyfills.js"></script>
    <script src="node_modules/systemjs/dist/system.src.js"></script>
    <script src="node_modules/rxjs/bundles/Rx.js"></script>
    <script src="node_modules/angular2/bundles/angular2.dev.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- 2. Configure SystemJS -->
    <script>
      var CONTEXT_PATH = "<%=request.getContextPath()%>";
      System.config({
        packages: {
          app: {
            format: 'register',
            defaultExtension: 'js'
          }
        }
      });
      System.import('app/main')
            .then(null, console.error.bind(console));
    </script>

  </head>

  <!-- 3. Display the application -->
  <body>
    <main-app>Loading...</main-app>
  </body>

</html>