
export default function callServer(endpoint: string, attr: any = {}) {
    const {body, ...customConfig} = attr
    const headers: any = {}
    const config = {
        method: body ? 'POST' : 'GET',
        credentials: 'include',
        ...customConfig,
        headers: {
            ...headers,
            ...customConfig.headers,
        },
    }
    if (body) {
        config.body =  body instanceof FormData ? body : JSON.stringify(body)
    }

    return window
        .fetch(`http://localhost:9090/${endpoint}`, config)
        .then(r => r.json())
}