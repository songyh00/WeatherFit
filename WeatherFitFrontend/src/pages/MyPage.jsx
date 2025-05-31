// MyPage.jsx
import '../App.css';
import {
    Container,
    Section,
    Footer,
    SectionTitle,
    GlobalStyle
} from "../layout/Mypage.style.js";
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination } from 'swiper/modules';
import { useEffect, useState } from 'react';
import React from "react";
import CloseBtnLogo from "../components/CloseBtnLogo.jsx";
import { ModalCloseBtn } from "../layout/WeatherSection.style.js";

const ImageSlider = ({ title, images }) => {
    const [modalImage, setModalImage] = useState(null);

    const closeModal = () => setModalImage(null);

    return (
        <div>
            <h2>{title}</h2>
            <Swiper
                slidesPerView={5}
                slidesPerGroup={1}
                loop={true}
                loopedSlides={images.length}
                spaceBetween={10}
                modules={[Navigation, Pagination]}
                navigation
                pagination={false}
                style={{ width: '800px', backgroundColor: '#fff', padding: '10px', borderRadius: '8px' }}
            >
                {images.length > 0 ? images.map((img, idx) => (
                    <SwiperSlide key={idx}>
                        <img
                            src={img.imageUrl}
                            alt={img.name}
                            style={{
                                width: '100%',
                                height: '200px',
                                objectFit: 'cover',
                                display: 'block',
                                cursor: 'pointer',
                            }}
                            onClick={() => setModalImage(img.imageUrl)}
                        />
                    </SwiperSlide>
                )) : (
                    Array.from({ length: 5 }).map((_, idx) => (
                        <SwiperSlide key={idx}>
                            <div style={{
                                width: '100%',
                                height: '200px',
                                backgroundColor: '#f5f5f5',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                color: '#999',
                                fontSize: '1rem',
                                border: '1px dashed #ccc'
                            }}>
                                찜한 옷이 없습니다
                            </div>
                        </SwiperSlide>
                    ))
                )}
            </Swiper>
            {modalImage && (
                <div
                    onClick={closeModal}
                    style={{
                        position: 'fixed',
                        top: 0, left: 0, right: 0, bottom: 0,
                        backgroundColor: 'rgba(0,0,0,0.8)',
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        zIndex: 1000,
                    }}
                >
                    <img
                        src={modalImage}
                        alt="modal"
                        style={{
                            maxWidth: '90vw',       // 너비는 뷰포트 90%로 제한
                            maxHeight: '80vh',      // 높이는 뷰포트 80%로 제한
                            objectFit: 'contain',   // 비율 유지하면서 컨테이너 안에 맞춤
                            borderRadius: '12px',
                            boxShadow: '0 0 20px rgba(255,255,255,0.8)',
                        }}
                        onClick={e => e.stopPropagation()} // 모달 닫기 방지
                    />
                    <ModalCloseBtn
                        onClick={closeModal}
                        style={{
                            position: 'absolute',
                            top: 20,
                            right: 20,
                            fontSize: '2rem',
                            color: 'white',
                            background: 'transparent',
                            border: 'none',
                            cursor: 'pointer',
                        }}
                    >
                        <CloseBtnLogo />
                    </ModalCloseBtn>
                </div>
            )}
        </div>
    );
};

const MyPage = () => {
    const [outerImages, setOuterImages] = useState([]);
    const [topImages, setTopImages] = useState([]);
    const [bottomImages, setBottomImages] = useState([]);

    useEffect(() => {
        fetch("/api/likes/mypage", {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => res.json())
            .then(data => {
                setOuterImages(data.filter(item => item.category === "아우터"));
                setTopImages(data.filter(item => item.category === "상의" || item.category === "원피스"));
                setBottomImages(data.filter(item => item.category === "하의"));
            })
            .catch(err => {
                console.error("❌ 좋아요 목록 가져오기 실패:", err);
            });
    }, []);

    const handleSubmit = () => {
        if (!window.confirm("정말로 회원 탈퇴하시겠습니까? 탈퇴 후 계정은 복구할 수 없습니다.")) return;

        fetch("/api/auth/withdraw", {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => {
                if (!res.ok) throw new Error("회원 탈퇴에 실패했습니다.");

                alert("회원 탈퇴가 완료되었습니다.");
                localStorage.removeItem("token");
                window.location.href = "/";
            })
            .catch(err => {
                alert("❌ " + err.message);
                console.error("회원 탈퇴 오류:", err);
            });
    };

    return (
        <Container>
            <GlobalStyle />
            <Section>
                <SectionTitle>❤️ 내가 좋아요 누른 목록</SectionTitle>
                <ImageSlider title="👕 아우터" images={outerImages} /><br /><br />
                <ImageSlider title="👔 상의" images={topImages} /><br /><br />
                <ImageSlider title="👖 하의" images={bottomImages} />
            </Section>

            <Footer onClick={handleSubmit}>
                🏠 [회원 탈퇴하기]
            </Footer><br /><br />
        </Container>
    );
};

export default MyPage;
