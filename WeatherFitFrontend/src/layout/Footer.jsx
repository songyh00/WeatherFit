import React from 'react';
import {
    FooterContainer,
    Row,
    FooterCol,
    FooterList,
    FooterItem,
    FooterLink,
    SocialLinks,
    SocialLink,
} from "./footer.style.js";


const Footer = () => {
    return (
        <FooterContainer>
            <div className="container">
                <Row>
                    <FooterCol>
                        <h4>CALL & CENTER</h4>
                        <FooterList>
                            <FooterItem>
                                AM 8:30 - PM 7:00 <br />
                                BREAK PM 12:30 - 01:30 <br />
                                <h3>010-123-456</h3>
                            </FooterItem>
                        </FooterList>
                    </FooterCol>
                    <FooterCol>
                        <h4>GUIDE</h4>
                        <FooterList>
                            <FooterItem><FooterLink to="/Best">상품 문의</FooterLink></FooterItem>
                            <FooterItem><FooterLink to="/CustomerServiceCenter">고객센터</FooterLink></FooterItem>
                            <FooterItem><FooterLink to="/Best">1:1 문의</FooterLink></FooterItem>
                        </FooterList>
                    </FooterCol>
                    <FooterCol>
                        <h4>INFO</h4>
                        <FooterList>
                            <FooterItem><FooterLink to="/Best">BEST</FooterLink></FooterItem>
                            <FooterItem><FooterLink to="/Suggestion">추천</FooterLink></FooterItem>
                            <FooterItem><FooterLink to="/Outerwear">상의</FooterLink></FooterItem>
                            <FooterItem><FooterLink to="/Consultation">하의</FooterLink></FooterItem>
                            <FooterItem><FooterLink to="/Pants">신발</FooterLink></FooterItem>
                        </FooterList>
                    </FooterCol>
                    <FooterCol>
                        <h4>Contact</h4>
                        <SocialLinks>
                            <SocialLink href="#"><i className="fab fa-facebook-f"></i></SocialLink>
                            <SocialLink href="#"><i className="fab fa-youtube"></i></SocialLink>
                            <SocialLink href="#"><i className="fab fa-instagram"></i></SocialLink>
                        </SocialLinks>
                    </FooterCol>
                </Row>
            </div>
        </FooterContainer>
    );
}
export default Footer;