import React from 'react'
import ApiClient from "./ApiClient";

const AuthContext = React.createContext({})

function AuthProvider(props: any){
    let data = new URLSearchParams(); // { username: 'test', password: 'test' }
    // @ts-ignore
    for (const pair of new FormData(window.document.forms[0])) {
        data.append(pair[0], pair[1]);
    }
    // @ts-ignore
    data = new FormData()
    data.append('username', 'test')
    data.append('password', 'test')
    console.log(data)
    const login = () => ApiClient('login', {body: data}).then((resp: any) => console.log(resp))
    const logout = () => console.log('logout')
    const user = { authenticated: false }

    return <AuthContext.Provider value={{login, logout, user}} {...props} />
}

function useAuth(){
    const ctx = React.useContext(AuthContext)
    if (ctx === undefined) {
        throw new Error(`useAuth must be used within a AuthProvider`)
    }
    return ctx
}

export { AuthProvider, useAuth}