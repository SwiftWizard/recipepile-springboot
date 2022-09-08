package com.recipepile.services;

import com.recipepile.domain.Category;
import com.recipepile.domain.ContentState;
import com.recipepile.domain.ContentStateDecision;
import com.recipepile.domain.Recipe;
import com.recipepile.domain.dtos.RecipeSlimDTO;
import com.recipepile.exceptions.CategoryNotFoundException;
import com.recipepile.mappers.RecipeMapper;
import com.recipepile.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class RecipeServiceImpl implements RecipeService{
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Collection<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Collection<Recipe> getRecipes(Category category) {
        return recipeRepository.findAllByCategory(category);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) throws CategoryNotFoundException {
        recipe.setRecipeId(sequenceGeneratorService.generateSequence("recipes_sequence"));

        User author = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setAuthor(userService.findByEmail(author.getUsername()));

        ContentStateDecision decision = new ContentStateDecision();
        decision.setDecisionTime(LocalDateTime.now());
        decision.setRationale("Content is automatically approved when created.");
        decision.setContentState(ContentState.APPROVED);

        recipe.addContentStateDecision(decision);

        boolean categoryDoesNotExists = false;

        for(int i = 0; i < recipe.getCategories().size(); i++){
            if(!categoryService.categoryExists(recipe.getCategories().get(i))){
                categoryDoesNotExists = true;
            }
        }

        if(categoryDoesNotExists){
            throw new CategoryNotFoundException("At least one of the supplied categories does not exist in the application!");
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public Collection<Recipe> getTopRecipes() {
        return recipeRepository.getTopRecipes();
    }

    @Override
    public Page<RecipeSlimDTO> getAll(Category category, Pageable pageable) {
        Query query = new Query().with(pageable);
        final List<Criteria> criteria = new ArrayList<>();

        if(category != null){
            criteria.add(Criteria.where("categories").in(category));
        }

        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return PageableExecutionUtils.getPage(recipeMapper.recipesToRecipeThinDTOs(mongoTemplate.find(query, Recipe.class)), pageable, () -> mongoTemplate.count(query.skip(0).limit(0), RecipeSlimDTO.class));
    }

    @Override
    public Recipe findById(Long id) {
        return this.recipeRepository.findById(id).get();
    }

}
