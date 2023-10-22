package kr.co.lotteon.service;

import kr.co.lotteon.dto.product.*;
import kr.co.lotteon.entity.product.ProductCate1Entity;
import kr.co.lotteon.entity.product.ProductCate2Entity;
import kr.co.lotteon.entity.product.ProductEntity;
import kr.co.lotteon.mapper.ProductMapper;
import kr.co.lotteon.repository.product.ProductCate1Repository;
import kr.co.lotteon.repository.product.ProductCate2Repository;
import kr.co.lotteon.repository.product.ProductRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository prodrepo;
    private final ProductCate1Repository prodCate1Repo;
    private final ProductCate2Repository prodCate2Repo;
    private final ModelMapper modelMapper;
    private final ProductMapper prodMapper;

    public PageResponseDTO findByCate1Product(PageRequestDTO pageRequestDTO) {

        log.info("pageRequestDTO.getCate1():" + pageRequestDTO.getProdCate1());
        Pageable pageable;
            if(pageRequestDTO.getOrderBy().equals("A")) {
                pageable = pageRequestDTO.getPageableA(pageRequestDTO.getSort()); // Pageable 가져오는 방법 수정
            }else {
                pageable = pageRequestDTO.getPageableD(pageRequestDTO.getSort()); // Pageable 가져오는 방법 수정
            }


        log.info("pageRequestDTO.getCate1():" + pageRequestDTO.getProdCate1());
        
        
        Page<ProductEntity> result = prodrepo.findProductEntitiesByprodCate1(pageRequestDTO.getProdCate1(), pageable); //

        log.info("result" + result); // 100
        log.info("result.getTotalElements():" + result.getTotalElements()); // 100
        log.info("result.getContent():" + result.getContent()); // 100
        
        List<ProductDTO> dtoList = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class)) // ModelMapper 사용 방법 수정 //map.()의 파라미터중 앞에는 소스 뒤는 목적하는 타겟의 타입을 명시
                .toList();
    
        log.info("dtoList.size():" + dtoList.size()); // 10
        log.info("dtoList:" + dtoList); // 100
        log.info(""+dtoList.get(0).getProdNo()); // 100
        
        int totalElement = (int) result.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO) // pageRequestDTO 수정
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }


    public PageResponseDTO findByCate2Product(PageRequestDTO pageRequestDTO) {

        log.info("pageRequestDTO.getCate1() 73번라인:" + pageRequestDTO.getProdCate1());
        log.info("pageRequestDTO.getCate2() 73번라인:" + pageRequestDTO.getProdCate2());
        Pageable pageable;
        if(pageRequestDTO.getOrderBy().equals("A")) {
            pageable = pageRequestDTO.getPageableA(pageRequestDTO.getSort()); // Pageable 가져오는 방법 수정
        }else {
            pageable = pageRequestDTO.getPageableD(pageRequestDTO.getSort()); // Pageable 가져오는 방법 수정
        }


        log.info("pageRequestDTO.getCate2() 84번라인:" + pageRequestDTO.getProdCate2());
        log.info("pageRequestDTO.getCate1() 84번라인:" + pageRequestDTO.getProdCate1());

        Page<ProductEntity> result = prodrepo.findProductEntitiesByProdCate1AndProdCate2(pageRequestDTO.getProdCate1(), pageRequestDTO.getProdCate2(), pageable); //

        log.info("result" + result); // 100
        log.info("result.getTotalElements():" + result.getTotalElements()); // 100
        log.info("result.getContent():" + result.getContent()); // 100

        List<ProductDTO> dtoList = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class)) // ModelMapper 사용 방법 수정 //map.()의 파라미터중 앞에는 소스 뒤는 목적하는 타겟의 타입을 명시
                .toList();

        log.info("dtoList.size():" + dtoList.size()); // 10
        log.info("dtoList:" + dtoList); // 100
        log.info(""+dtoList.get(0).getProdNo()); // 100

        int totalElement = (int) result.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO) // pageRequestDTO 수정
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }
    
    
    public List<ProductCate2DTO> getAllProdCates() {
        
        List<ProductCate2DTO> prodCate2DTO = prodMapper.selectProductCatesMain();
  
        log.info("prodCate2DTO:" + prodCate2DTO);


        return prodCate2DTO;
    }
    
    public List<ProductEntity> getAllProduct(int prodNo) {
     
        log.info("prodNo:" + prodNo);
        
        List<ProductEntity> productEntity = prodrepo.findByProdNo(prodNo);
        log.info("productEntity:" + productEntity);

        return productEntity;
    }


    public List<ProductCate1DTO> getAllProdCate1s() {
        
        List<ProductCate1DTO> prodCate1DTO = prodMapper.selectProductCate1sMain();
        
        
        
        
        return prodCate1DTO;
    }

    public List<ProductCate1DTO> getCate() {
        List<ProductCate1Entity> productCate1Entities = prodCate1Repo.findAll();
        List<ProductCate2Entity> productCate2Entities = prodCate2Repo.findAll();
        List<ProductCate1DTO> productCate1DTOS = new ArrayList<>();
        for (ProductCate1Entity productCate1Entity : productCate1Entities) {
            ProductCate1DTO productCate1DTO = productCate1Entity.toDTO();
            List<ProductCate2DTO> productCate2DTOS = new ArrayList<>();
            for (ProductCate2Entity productCate2Entity : productCate2Entities) {
                ProductCate2DTO productCate2DTO = productCate2Entity.toDTO();
                if (productCate2DTO.getCate1() == productCate1DTO.getCate1()) {
                    productCate2DTOS.add(productCate2DTO);
                }
            }
            productCate1DTO.setCate2s(productCate2DTOS);
            productCate1DTOS.add(productCate1DTO);
        }
        return productCate1DTOS;
    }
    
    
}