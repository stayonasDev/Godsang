package godsang.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = 1460200420L;

    public static final QFood food = new QFood("food");

    public final NumberPath<Double> carb = createNumber("carb", Double.class);

    public final NumberPath<Double> cholest = createNumber("cholest", Double.class);

    public final NumberPath<Double> gram = createNumber("gram", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> kal = createNumber("kal", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Double> pat = createNumber("pat", Double.class);

    public final NumberPath<Double> protein = createNumber("protein", Double.class);

    public final NumberPath<Double> sodium = createNumber("sodium", Double.class);

    public final NumberPath<Double> sugar = createNumber("sugar", Double.class);

    public QFood(String variable) {
        super(Food.class, forVariable(variable));
    }

    public QFood(Path<? extends Food> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFood(PathMetadata metadata) {
        super(Food.class, metadata);
    }

}

