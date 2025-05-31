// src/api/like.jsx
export const useLikeApi = () => {
    const token = localStorage.getItem("token");

    /**
     * 내가 좋아요한 옷 리스트 조회 (마이페이지용)
     * GET /api/likes/mypage
     */
    const getMyLikes = async () => {
        try {
            const res = await fetch("/api/likes/mypage", {
                headers: { Authorization: `Bearer ${token}` },
            });
            if (!res.ok) throw new Error("좋아요 목록 불러오기 실패");
            const data = await res.json(); // [{ id, image, name, category }, ...]
            return data;
        } catch (err) {
            console.error("❌ 좋아요 목록 실패:", err);
            return [];
        }
    };

    /**
     * 좋아요 추가 또는 취소 (토글 방식)
     * POST /api/likes/{clothesId}
     */
    const toggleLike = async (clothesId) => {
        try {
            const res = await fetch(`/api/likes/${clothesId}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (!res.ok) throw new Error("좋아요 상태 변경 실패");
        } catch (err) {
            console.error(`❌ 좋아요 토글 실패 (ID=${clothesId}):`, err);
        }
    };

    return { getMyLikes, toggleLike };
};
