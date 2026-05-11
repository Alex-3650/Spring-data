package soft_uni.mvc.services.company;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.InputCompanyDto;
import soft_uni.mvc.entities.Company;
import soft_uni.mvc.repositories.CompanyRepository;

import javax.activation.CommandMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CompanyDto create(InputCompanyDto company) {

            Company companyMapped = this.modelMapper.map(company, Company.class);
            this.companyRepository.save(companyMapped);
            return this.modelMapper.map(companyMapped, CompanyDto.class);
    }

    @Override
    public long count() {
        return this.companyRepository.count();
    }

    @Override
    public Company getReferenceById(long id) {
        return this.companyRepository.getReferenceById(id);
    }

    @Override
    public List<CompanyDto> findManyByName(Collection<String> names) {
        List<Company> allByNameIn = this.companyRepository.findAllByNameIn(names);
        List<CompanyDto> result = new ArrayList<>();

        for (Company company : allByNameIn) {
            CompanyDto mapped = this.modelMapper.map(company, CompanyDto.class);
            result.add(mapped);
        }
        return result;
    }
}
