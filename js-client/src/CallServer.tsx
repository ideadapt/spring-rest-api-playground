
export default async function callServer(attr: any = {}) {
    const {endpoint, body, ...customConfig} = attr
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

    const result = await window.fetch(`http://localhost:9090/${endpoint}`, config)
    if (!result.ok) throw new Error(result.statusText)
    return result.json()
}