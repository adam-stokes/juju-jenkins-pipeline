import groovy.transform.Field

@Field cipy = "workon jenkins && python3.6"
@Field pytest = "workon jenkins && pytest -v -s --tb native -ra"
@Field cipaths = "/snap/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/bin"
