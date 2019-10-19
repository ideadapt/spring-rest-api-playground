import React, {FormEvent} from 'react';
import './App.css';
import UserList from "./UserList";
import {AuthProvider, useAuth} from "./AuthContext";

const AuthableApp = () => {
    const auth = useAuth() as { user: { authenticated: boolean }, login: any, logout: any }

    const login = (ev: FormEvent) => {
        ev.preventDefault()
        // @ts-ignore
        const {username, password} = ev.target.elements
        auth.login({username: username.value, password: password.value})
    }

    return (<>
        {auth.user.authenticated ?
            <main>
                <UserList/>
            </main>
            : <form onSubmit={login}>
                <input name='username' value={'test'}/>
                <input name='password' value={'test'}/>
                <input type='submit' value='login'/>
            </form>}
    </>)
}

const App: React.FC = () => {
    return (
        <AuthProvider>
            <AuthableApp/>
        </AuthProvider>
    );
}

export default App;
