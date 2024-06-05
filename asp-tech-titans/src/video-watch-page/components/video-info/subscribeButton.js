import React from 'react';
import './subscribeButton.css';

function subscribeButton({ onClick, setUsers, currentUser, publisher }) {

    const isSubscribed = currentUser.subscriptions.includes(publisher);

    const switchButtons = () => {
        if (isSubscribed) {
            removeSubscription();
        } else {
            addSubscription();
        }
    }

    const removeSubscription = () => {
        setUsers(prevUsers => {
            const updatedUsers = [...prevUsers];
            if (currentUser && currentUser.subscriptions) {
                currentUser.subscriptions = currentUser.subscriptions.filter(sub => sub !== publisher);
            }
            return updatedUsers;
        });
    };

    const addSubscription = () => {
        setUsers(prevUsers => {
            const updatedUsers = [...prevUsers];
            if (currentUser && currentUser.subscriptions) {
                currentUser.subscriptions.push(publisher);
            }
            return updatedUsers;
        });
    };

    return (
        <button type="button" onClick={switchButtons} className={ isSubscribed ? "btn btn-light unsubscribe-button" : "btn btn-dark subscribe-button"}>
            {isSubscribed ? "unsubscribe" : "subscribe"}
        </button>
    );
}

export default subscribeButton;
