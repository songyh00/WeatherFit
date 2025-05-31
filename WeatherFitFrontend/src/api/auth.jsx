// src/api/auth.jsx

export const useAuthApi = () => {
    const token = localStorage.getItem("token");

    const getAuthHeaders = () => ({
        "Content-Type": "application/json",
        ...(token && { Authorization: `Bearer ${token}` })
    });

    const login = async (credentials) => {
        const res = await fetch("/api/auth/login", {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(credentials),
        });

        if (!res.ok) throw new Error("로그인 실패");
        return await res.json();
    };

    const register = async (userInfo) => {
        const res = await fetch("/api/auth/register", {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(userInfo),
        });

        if (!res.ok) throw new Error("회원가입 실패");
        return await res.json();
    };

    return {
        login,
        register,
    };
};
