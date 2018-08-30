/* Grab a few logs
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    dir("debug-out") {
        sh "juju status -m ${controller}:${model} --format yaml | tee juju-status.yaml"
        sh "juju debug-log -m ${controller}:${model} --replay | tee debug-log.txt"
        sh "juju storage -m ${controller}:${model} --format yaml | tee storage.yaml"
        sh "juju storage-pools -m ${controller}:${model} --format yaml | tee storage-pools.yaml"
        sh "juju config -m ${controller}:${model} kubernetes-master --format yaml | tee kubernetes-master.yaml"
        sh "juju config -m ${controller}:${model} kubernetes-worker --format yaml | tee kubernetes-worker.yaml"
        sh "juju config -m ${controller}:${model} kubernetes-load-balancer --format yaml | tee kubernetes-load-balancer.yaml"
        sh "juju config -m ${controller}:${model} etcd --format yaml | tee etcd.yaml"
        sh "juju config -m ${controller}:${model} easyrsa --format yaml | tee easyrsa.yaml"
        sh "juju config -m ${controller}:${model} flannel --format yaml | tee flannel.yaml"
    }
    archiveArtifacts artifacts: 'debug-out/*', fingerprint: true
}
