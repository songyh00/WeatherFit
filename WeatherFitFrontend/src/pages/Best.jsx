// Best.jsx
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

const Best = ({ recommendationData, isLoading }) => {
    const { getMyLikes, toggleLike } = useLikeApi(); // ✅ 수정
    const [favorites, setFavorites] = useState([]);

    useEffect(() => {
        const fetchFavorites = async () => {
            const liked = await getMyLikes();
            const likedIds = liked.map(item => item.id); // ✅ id만 추출
            setFavorites(likedIds);
        };
        fetchFavorites();
    }, []);

    const handleToggleFavorite = async (id) => {
        try {
            await toggleLike(id); // ✅ 서버에 토글 요청
            setFavorites(prev =>
                prev.includes(id)
                    ? prev.filter(favId => favId !== id) // 좋아요 취소
                    : [...prev, id] // 좋아요 추가
            );
        } catch (err) {
            console.error("좋아요 처리 실패:", err);
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

    const getClothesByCategory = (category) => {
        return recommendationData?.recommendedClothes?.filter(item => item.category === category) || [];
    };

    const getTopAndOnePiece = () => {
        return recommendationData?.recommendedClothes?.filter(
            item => item.category === "상의" || item.category === "원피스"
        ) || [];
    };

    return (
        <ContentsWrapper>
            <MainContents>
                {!isLoading && recommendationData && (
                    <>
                        {renderCategoryCards(getClothesByCategory("아우터"))}
                        {renderCategoryCards(getTopAndOnePiece())}
                        {renderCategoryCards(getClothesByCategory("하의"))}
                    </>
                )}
                {!isLoading && recommendationData?.recommendedClothes?.length === 0 && (
                    <p>추천된 옷이 없습니다.</p>
                )}
            </MainContents>
        </ContentsWrapper>
    );
};

export default Best;
