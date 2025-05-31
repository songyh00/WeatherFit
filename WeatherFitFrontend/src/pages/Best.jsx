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

const Best = ({ recommendationData, isLoading }) => {
    const [favorites, setFavorites] = useState([]);

    useEffect(() => {
        const storedFavorites = JSON.parse(localStorage.getItem('favorites')) || [];
        setFavorites(storedFavorites);
    }, []);

    const toggleFavorite = (id) => {
        const updatedFavorites = favorites.includes(id)
            ? favorites.filter((favoriteId) => favoriteId !== id)
            : [...favorites, id];

        setFavorites(updatedFavorites);
        localStorage.setItem('favorites', JSON.stringify(updatedFavorites));
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
                        onClick={() => toggleFavorite(item.id)}
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

    return (
        <ContentsWrapper>
            <MainContents>
                {!isLoading && recommendationData && (
                    <>
                        {renderCategoryCards(getClothesByCategory("아우터"))}
                        {renderCategoryCards(getClothesByCategory("상의"))}
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
