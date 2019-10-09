import React, {useEffect, useState} from "react";

const UserList: React.FC = () => {
    let [users, setUsers] = useState([])

    useEffect(() => {
        fetch('http://localhost:9090/users').then(async (res) => {
            setUsers(await res.json())
        });
    }, [])

    return (
        <div className="App">
            {
                users.map((user: any) => <div>
                    {user.id}: {user.name}
                </div>)
            }
        </div>
    );
};

export default UserList;