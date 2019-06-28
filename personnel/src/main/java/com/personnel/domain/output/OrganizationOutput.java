package com.personnel.domain.output;



import com.common.Enum.IsSyncQueueNameEnum;
import com.personnel.model.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrganizationOutput extends Organization {
    private List<OrganizationOutput> children = null;


    private  Integer key;

    public OrganizationOutput(Organization organization) {
        this.setId(organization.getId());
        this.key = organization.getId();
        this.setOrganizationNo( organization.getOrganizationNo());
        this.setDisplayOrder(organization.getDisplayOrder());
        this.setName(organization.getName());
        this.setParentId(organization.getParentId());
        this.setOfficePhone(organization.getOfficePhone());
        this.setPhoneNumber(organization.getPhoneNumber());
        this.setAddress(organization.getAddress());
        this.setChildren(null);
        this.setIsSyncQueue(organization.getIsSyncQueue());
    }

    private  String parentName;

    private  String leadershipName;

    private  String departmentalManagerName;

    public OrganizationOutput(Organization organization,String parentName,String leadershipName,String departmentalManagerName ) {
        this.setOrganizationNo(organization.getOrganizationNo()) ;
        this.setOrganizationCode(organization.getOrganizationCode());
        this.setName(organization.getName());
        this.setParentId(organization.getParentId());
        this.setType(organization.getType());
        this.setAssessmentState(organization.getAssessmentState());
        this.setPhoneNumber(organization.getPhoneNumber());
        this.setOfficePhone(organization.getOfficePhone());
        this.setFax(organization.getFax());
        this.setAddress(organization.getAddress());
        this.setDescription(organization.getDescription());
        this.setDepartmentalManager(organization.getDepartmentalManager());
        this.setLeadership(organization.getLeadership());
        this.setCreatedUserName(organization.getCreatedUserName());
        this.setCreatedUserId(organization.getCreatedUserId());
        this.setCreatedDateTime(organization.getCreatedDateTime());
        this.setLastUpdateDateTime(organization.getLastUpdateDateTime());
        this.setLastUpdateUserId(organization.getLastUpdateUserId());
        this.setLastUpdateUserName(organization.getLastUpdateUserName());
        this.setId(organization.getId());
        this.setParentName(parentName);
        this.setDepartmentalManagerName(departmentalManagerName);
        this.setLeadershipName(leadershipName);
        this.setDisplayOrder(organization.getDisplayOrder());
        this.setFirstLetter(organization.getFirstLetter());
        this.setLinkedId(organization.getLinkedId());
    }
}
