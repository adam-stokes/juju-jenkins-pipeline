// Runs a command on a remote instance

def call(ipaddr, cmd) {
    sh "ssh -i /var/lib/jenkins/.ssh/cdkbot_rsa -oStrictHostKeyChecking=no ubuntu@${ipaddr} '${cmd}'"
}
