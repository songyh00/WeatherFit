// src/api/weather.jsx

export const useWeatherApi = () => {
    const fetchWeather = async (address, isTomorrow) => {
        try {
            const res = await fetch(`/api/weather?address=${encodeURIComponent(address)}${isTomorrow ? "&tomorrow=true" : ""}`);
            if (!res.ok) throw new Error("날씨 요청 실패");
            return await res.json();
        } catch (err) {
            console.error("❌ 날씨 조회 실패:", err);
            return null;
        }
    };

    return { fetchWeather };
};
