const testServer = 'http://localhost:8080/rs';

// Cannot achieve coverage of 100% because of testing environment.
export const baseURL = (process.env.NODE_ENV === 'development' ? testServer : '/rs');

export const createQueryParams = params => Object.keys(params)
    .map(key => `${key}=${encodeURI(params[key])}`)
    .join('&');

export const getServiceURL = (serviceURL, params) => {
    if (params && Object.keys(params).length) {
        return `${baseURL}/${serviceURL}?${createQueryParams(params)}`;
    }

    return `${baseURL}/${serviceURL}`;
};

// ATTENTION: Only for the Login Process.
export const getLoginHeaders = (username, password) => ({
    'Authentication-Username': username,
    'Authentication-Password': password,
});

// ATTENTION: For everything except the Login Process.
export const getAuthenticationHeaders = (userId, token) => ({
    'Authentication-User-Id': userId,
    'Authentication-Token': token,
});

export const handleHTTPErrors = (response) => {
    if (!response.ok) {
        throw Error(response.statusText);
    }

    return response;
};
