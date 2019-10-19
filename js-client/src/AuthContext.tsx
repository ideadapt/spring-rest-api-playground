import React from 'react'
import callServer from "./CallServer";
import {useAsync} from "react-async";

const AuthContext = React.createContext({})

function AuthProvider(props: any){
    // @ts-ignore
    const {data, error = { message: '' } , isRejected, isPending, isSettled, reload} = useAsync({promiseFn: callServer, endpoint: 'session'})
    const login = ({username, password}: {username: string, password: string}) => {
        const data = new FormData()
        data.append('username', username)
        data.append('password', password)

        callServer({ endpoint: 'login', body: data}).then((resp: any) => {
            // @ts-ignore
            data.authenticated = true
            reload()
        })
    }
    const logout = () => console.log('logout')

    const [firstAttemptFinished, setFirstAttemptFinished] = React.useState(false)
    React.useLayoutEffect(() => {
        if (isSettled) {
            setFirstAttemptFinished(true)
        }
    }, [isSettled])

    if (!firstAttemptFinished) {
        if (isPending) {
            return <span>LOADING!</span>
        }
        if (isRejected) {
            return (
                <div>
                    <pre>{error.message}</pre>
                </div>
            )
        }
    }
    console.log(data)
    return <AuthContext.Provider value={{login, logout, session: data }} {...props} />
}

function useAuth(){
    const ctx = React.useContext(AuthContext)
    if (ctx === undefined) {
        throw new Error(`useAuth must be used within a AuthProvider`)
    }
    return ctx
}

export { AuthProvider, useAuth}