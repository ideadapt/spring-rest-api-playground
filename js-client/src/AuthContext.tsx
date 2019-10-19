import React from 'react'
import callServer from "./CallServer";

const AuthContext = React.createContext({})

function AuthProvider(props: any){
    const user = { authenticated: false }
    const login = ({username, password}: {username: string, password: string}) => {
        const data = new FormData()
        data.append('username', username)
        data.append('password', password)

        callServer('login', {body: data}).then((resp: any) => {
            user.authenticated = true
        })
    }
    const logout = () => console.log('logout')

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