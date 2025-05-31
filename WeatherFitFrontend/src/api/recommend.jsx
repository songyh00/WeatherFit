// src/api/recommend.jsx

export const useRecommendApi = () => {
    const token = localStorage.getItem("token");

    const fetchRecommendation = async (address, isTomorrow, bannerType) => {
        try {
            const res = await fetch("/api/recommend", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify({
                    address,
                    tomorrow: isTomorrow,
                    bannerType,
                }),
            });

            if (!res.ok) throw new Error("코디 추천 실패");
            return await res.json();
        } catch (err) {
            console.error("❌ 추천 실패:", err);
            return null;
        }
    };

    return { fetchRecommendation };
};
