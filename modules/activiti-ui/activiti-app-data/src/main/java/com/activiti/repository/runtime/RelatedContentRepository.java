/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.activiti.repository.runtime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.activiti.domain.idm.User;
import com.activiti.domain.runtime.RelatedContent;

/**
 * @author Frederik Heremans
 * @author Erik Winlof
 */
public interface RelatedContentRepository extends JpaRepository<RelatedContent, Long> {

    Page<RelatedContent> findAllRelatedBySourceAndSourceId(@Param("source")String source, @Param("sourceId")String sourceId, Pageable paging);

    @Query("from RelatedContent r where r.taskId = :taskId and r.relatedContent = true")
    Page<RelatedContent> findAllRelatedByTaskId(@Param("taskId") String taskId, Pageable paging);
    
    @Query("from RelatedContent r where r.taskId = :taskId and r.relatedContent = false")
    Page<RelatedContent> findAllFieldBasedContentByTaskId(@Param("taskId") String taskId, Pageable paging);
    
    Page<RelatedContent> findAllByTaskIdAndField(@Param("taskId") String taskId, @Param("field") String field, Pageable paging);
    
    @Query("from RelatedContent r where r.processInstanceId = :processInstanceId and r.relatedContent = true")
    Page<RelatedContent> findAllRelatedByProcessInstanceId(@Param("processInstanceId")String processId, Pageable paging);
    
    @Query("from RelatedContent r where r.processInstanceId = :processInstanceId and r.relatedContent = false")
    Page<RelatedContent> findAllFieldBasedContentByProcessInstanceId(@Param("processInstanceId")String processId, Pageable paging);
    
    @Query("from RelatedContent r where r.processInstanceId = :processInstanceId")
    Page<RelatedContent> findAllContentByProcessInstanceId(@Param("processInstanceId")String processId, Pageable paging);

    @Query("from RelatedContent r where r.processInstanceId = :processInstanceId and r.field = :field")
    Page<RelatedContent> findAllByProcessInstanceIdAndField(@Param("processInstanceId")String processId, @Param("field") String field, Pageable paging);

    @Modifying
    @Query("delete from RelatedContent r where r.processInstanceId = :processInstanceId")
    void deleteAllContentByProcessInstanceId(@Param("processInstanceId") String processInstanceId);
    
    @Query("select sum(r.contentSize) from RelatedContent r where r.createdBy = :user")
    Long getTotalContentSizeForUser(@Param("user") User user);
}
