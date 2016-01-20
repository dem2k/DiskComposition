@call mvn -Djetty.reload=automatic -Djetty.scanIntervalSeconds=3 -Djetty.dumpOnStart=true jetty:run
@if errorlevel 1 pause
@rem
@rem -Djetty.scanIntervalSeconds=5
@rem    The pause in seconds between sweeps of the webapp to check for changes and automatically hot redeploy if any are detected. By default this is 0, which disables hot deployment scanning. A number greater than 0 enables it.
@rem -Djetty.reload=automatic
@rem    Default value is "automatic", used in conjunction with a non-zero scanIntervalSeconds causes automatic hot redeploy when changes are detected. Set to "manual" instead to trigger scanning by typing a linefeed in the console running the plugin. This might be useful when you are doing a series of changes that you want to ignore until you're done. In that use, use the reload parameter.
