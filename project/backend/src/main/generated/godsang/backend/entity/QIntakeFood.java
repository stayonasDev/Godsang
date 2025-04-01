package godsang.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIntakeFood is a Querydsl query type for IntakeFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntakeFood extends EntityPathBase<IntakeFood> {

    private static final long serialVersionUID = -1573794640L;

    public static final QIntakeFood intakeFood = new QIntakeFood("intakeFood");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QIntakeFood(String variable) {
        super(IntakeFood.class, forVariable(variable));
    }

    public QIntakeFood(Path<? extends IntakeFood> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIntakeFood(PathMetadata metadata) {
        super(IntakeFood.class, metadata);
    }

}

