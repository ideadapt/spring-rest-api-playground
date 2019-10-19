import React, {useEffect, useState} from "react";
import callServer from "./CallServer";

const UserList: React.FC = () => {
    let [users, setUsers] = useState([])

    useEffect(() => {
        callServer({endpoint: 'users'}).then(setUsers);
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