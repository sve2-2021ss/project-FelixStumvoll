package com.urlshortener.core.services.shorturl.impl

import com.urlshortener.ShortUrlConfiguration
import com.urlshortener.core.dtos.CreateShortUrlDto
import com.urlshortener.core.dtos.ShortUrlDto
import com.urlshortener.core.dtos.UpdateShortUrlDto
import com.urlshortener.core.exceptions.EntityNotFoundException
import com.urlshortener.core.exceptions.ShortNameAlreadyExistsException
import com.urlshortener.core.services.namegenerator.NameGenerator
import com.urlshortener.core.services.shorturl.ShortUrlService
import com.urlshortener.core.util.getException
import com.urlshortener.dal.entities.ShortUrl
import com.urlshortener.dal.entities.shortNameConstraint
import com.urlshortener.dal.repositories.ShortUrlRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionException
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import javax.validation.ConstraintViolationException
import org.hibernate.exception.ConstraintViolationException as HConstraintViolationException


@Service
class ShortUrlServiceImpl(
    private val shortUrlRepository: ShortUrlRepository,
    private val nameGenerator: NameGenerator,
    private val shortUrlConfiguration: ShortUrlConfiguration,
    private val transactionTemplate: TransactionTemplate
) : ShortUrlService {
    override fun findByShortname(shortName: String): ShortUrlDto =
        shortUrlRepository.findByShortName(shortName)?.toDto() ?: throw EntityNotFoundException(
            notFoundByShortNameMessage(shortName)
        )

    @Transactional(readOnly = true)
    override fun findAll(userId: Long): List<ShortUrlDto> =
        shortUrlRepository.findAllByUserId(userId).map { it.toDto() }

    @Transactional(readOnly = true)
    override fun findById(id: Long, userId: Long) = getById(id, userId).toDto()

    override fun update(entity: UpdateShortUrlDto): ShortUrlDto = withShortNameConstraintHandler(entity.shortName) {
        val persistedEntity = getById(entity.id, entity.userId)

        entity.shortName?.let {
            persistedEntity.shortName = it
        }

        entity.url?.let {
            persistedEntity.url = it
        }

        persistedEntity.toDto()
    }

    @Transactional
    override fun delete(id: Long, userId: Long) =
        if (shortUrlRepository.existsByIdAndUserId(id, userId)) {
            shortUrlRepository.deleteById(id)
        } else throw EntityNotFoundException(notFoundByIdMessage(id))

    override fun create(createShortUrlDto: CreateShortUrlDto): ShortUrlDto =
        withShortNameConstraintHandler(createShortUrlDto.shortName) {
            shortUrlRepository.save(
                ShortUrl(
                    createShortUrlDto.url,
                    createShortUrlDto.shortName ?: getUniqueShortName(),
                    createShortUrlDto.userId
                )
            ).toDto()
        }

    private fun getById(id: Long, userId: Long): ShortUrl =
        shortUrlRepository.findByIdAndUserId(id, userId) ?: throw EntityNotFoundException(
            notFoundByIdMessage(id)
        )

    private fun withShortNameConstraintHandler(
        shortName: String?,
        block: () -> ShortUrlDto
    ): ShortUrlDto = try {
        transactionTemplate.execute { block() }!!
    } catch (ex: DataIntegrityViolationException) {
        if (shortName != null) {
            ex.getException<HConstraintViolationException>()?.let {
                if (it.constraintName.contains(shortNameConstraint, ignoreCase = true)) {
                    throw ShortNameAlreadyExistsException(shortName)
                }
            }
        }

        throw ex
    } catch (ex: TransactionException) {
        ex.getException<ConstraintViolationException>()?.let {
            throw it
        }
        throw ex
    }

    fun ShortUrl.toDto(): ShortUrlDto = ShortUrlDto(shortName, url, id!!, userId)

    private fun getUniqueShortName(): String {
        val nameGenerator = nameGenerator::generateName
        var generatedName = nameGenerator(shortUrlConfiguration.generatedNameLength)

        while (shortUrlRepository.countByShortName(generatedName) != 0) {
            generatedName = nameGenerator(shortUrlConfiguration.generatedNameLength)
        }

        return generatedName
    }

    companion object {
        private fun notFoundByIdMessage(id: Long) = "ShortUrl with id $id not found"
        private fun notFoundByShortNameMessage(shortName: String) = "ShortUrl with name $shortName not found"
    }
}