
function ApiClient(endpoint: string, {body, ...customConfig}: any) {
    const token = window.localStorage.getItem('__bookshelf_token__')
    const headers = {}
    if (token) {
        // @ts-ignore
        headers.Authorization = `Bearer ${token}`
    }
    const config = {
        method: body ? 'POST' : 'GET',
        ...customConfig,
        headers: {
            ...headers,
            // @ts-ignore
            ...customConfig.headers,
        },
    }
    if (body) {
        // @ts-ignore
        config.body = body; // JSON.stringify(body)
    }

    return window
        .fetch(`http://localhost:9090/${endpoint}`, config)
        .then(r => r.json())
}

export default ApiClient