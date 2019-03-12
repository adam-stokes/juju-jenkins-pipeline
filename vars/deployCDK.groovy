/* Deploy CDK
 *
 * @param conf Map
 *    conf.controller juju controller to use
 *    conf.model juju model to use
 *    conf.cloud juju cloud in a cloud/region format
 *    conf.version_overlay path to bundle overlay for defining k8s versions
 *    conf.bundle_channel juju charmstore channel to pull bundle from
 *                        (note: this only affects the channel of the bundle)
 *    conf.charms_channel juju charmstore channel to force all charms within the bundle to
 *    conf.allow_privileged enable allow privileged in k8s master/worker
 *    conf.disable_add_model do not run add-model, this is due to factors such
 *                           as localhost that need lxd profiles update prior to deploy
 *    conf.disable_wait do not wait after deployment
 */
def call(Map conf) {
    if (!conf.cloud) {
        conf.cloud = 'aws'
    }
    if (!conf.bundle_channel) {
        conf.bundle_channel = 'edge'
    }
    if (!conf.charms_channel) {
        conf.charms_channel = ""  // prevent "null" in command
    } else {
        conf.charms_channel = "--channel ${conf.charms_channel}"
    }
    if (!conf.allow_privileged) {
        conf.allow_privileged = false
    }
    if (!conf.disable_add_model) {
        if (conf.cloud) {
            sh "juju add-model -c ${conf.controller} ${conf.model} ${conf.cloud}"
        } else {
            sh "juju add-model -c ${conf.controller} ${conf.model}"
        }
    }
    // There is no way to tell Juju to use a specific channel of a bundle but
    // to leave the contents unmodified, so we have to pull a local copy of the
    // channel that we want.
    sh "charm pull ${conf.bundle} --channel ${conf.bundle_channel} ./bundle-to-test"
    sh "juju deploy -m ${conf.controller}:${conf.model} ./bundle-to-test/bundle.yaml --overlay ${conf.version_overlay} ${conf.charms_channel}"
    if (conf.allow_privileged) {
        sh "juju config -m ${conf.controller}:${conf.model} kubernetes-master allow-privileged=true"
        sh "juju config -m ${conf.controller}:${conf.model} kubernetes-worker allow-privileged=true"
    }

    if (!conf.disable_wait) {
        sh "juju-wait -e ${conf.controller}:${conf.model} -w"
    }
}
