import React, { useState, useEffect } from "react";
import {
    ContentsWrapper,
    MainContents,
    Card,
    Content,
    Like,
    ClothesText,
    Wimg
} from "../layout/Best.style.js";
import { useLikeApi } from "../api/like";

const Outerwear = ({ recommendationData, isLoading }) => {
    const { getMyLikes, toggleLike } = useLikeApi(); // ✅ 통합된 토글 API
    const [favorites, setFavorites] = useState([]);

    // ✅ 좋아요 ID만 저장
    useEffect(() => {
        const fetchFavorites = async () => {
            const liked = await getMyLikes();
            const likedIds = liked.map(item => item.id);
            setFavorites(likedIds);
        };
        fetchFavorites();
    }, []);

    const handleToggleFavorite = async (id) => {
        try {
            await toggleLike(id);
            setFavorites(prev =>
                prev.includes(id)
                    ? prev.filter(favId => favId !== id)
                    : [...prev, id]
            );
        } catch (err) {
            console.error("❌ 좋아요 처리 실패:", err);
        }
    };

    const isFavorite = (id) => favorites.includes(id);

    const renderCategoryCards = (list) => {
        return list.map((item, idx) => (
            <Card key={`${item.category}-${idx}`}>
                <Wimg>
                    <img
                        src={item.imageUrl}
                        alt={item.name}
                    />
                </Wimg>
                <Content>
                    <Like
                        liked={isFavorite(item.id)}
                        onClick={() => handleToggleFavorite(item.id)}
                    >
                        {isFavorite(item.id) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                    </Like>
                    <ClothesText>{item.name}</ClothesText>
                </Content>
            </Card>
        ));
    };

    const getOuterwears = () => {
        return recommendationData?.recommendedClothes?.filter(item => item.category === "아우터") || [];
    };

    const outerwearList = getOuterwears();

    return (
        <ContentsWrapper>
            <MainContents>
                {!isLoading && recommendationData && outerwearList.length > 0 && (
                    renderCategoryCards(outerwearList)
                )}

                {!isLoading && recommendationData && outerwearList.length === 0 && (
                    <p style={{ fontSize: "1.1rem", marginTop: "20px", textAlign: "center" }}>
                        아우터를 입기엔 날씨가 덥습니다.<br />
                        그래도 입으시려면 얇은 아우터를 추천드립니다 🙂
                    </p>
                )}
            </MainContents>
        </ContentsWrapper>
    );
};

export default Outerwear;
