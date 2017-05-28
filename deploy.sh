#!/bin/bash
fuser -n tcp -k 8090 > redirection &
chmod 777 $WORKSPACE/build/libs/jmp.war
nohup nice java -jar $WORKSPACE/build/libs/jmp.war --spring.config.location=$WORKSPACE/src/main/resources/application.yml $> $WORKSPACE/jmp_deploy/initServer.log 2>&1 &

function watch(){

    tail -f $WORKSPACE/jmp_deploy/initServer.log |

        while IFS= read line
            do
                echo "Buffering: " "$line"

                if [[ "$line" == *"Started"* ]]; then
                    echo "Application Started... exiting buffer!"
                    pkill  tail
                fi
        done
}
watch